
var score = 1;
var currentRow = 0;
var currentMaze = undefined;
var previousMaze = undefined;
var nextMaze = undefined;
var cellSize = undefined;

function isBitSet(maze, bitPosition) {
    var arrayIndex = Math.floor(bitPosition / 8);
    var uintValue = maze.wallData[arrayIndex];
    var bitInValue = bitPosition % 8;
    var value = ((uintValue << (7 - bitInValue)) >> 7) & 1;
    return value == 1;
}

function hasTopWall(row, col, maze) {
    var bitPosition = (row * maze.width) + col;
    return isBitSet(maze, bitPosition);
}

function hasBottomWall(row, col, maze) {
    if (row == maze.height - 1) {
        // the bottom row of the maze
        var bitPosition = 2 * (maze.width * maze.height) + col;
        return isBitSet(maze, bitPosition);
    } else {
        // not the bottom row so use the top wall of the cell below the one specified
        return hasTopWall(row + 1, col, maze);
    }
}

function hasLeftWall(row, col, maze) {
    var bitPosition = (maze.width * maze.height) + (row * maze.width) + col;
    return isBitSet(maze, bitPosition);
}

function hasRightWall(row, col, maze) {
    if (col == maze.width - 1) {
        // the right-most column of the maze
        var bitPosition = 2 * (maze.width * maze.height) + maze.width + row;
        return isBitSet(maze, bitPosition);
    } else {
        // not the bottom row so use the left wall of the cell to the right of the one specified
        return hasLeftWall(row, col + 1, maze);
    }
}

function processMaze(maze) {
    var compressed = new Uint8Array(maze.serialized.match(/.{1,2}/g).map(byte => parseInt(byte, 16)));

    var path = [];
    for (var r = 0; r < maze.id.height; r++) {
        path[r] = [];
        for (var c = 0; c < maze.id.width; c++) {
            path[r][c] = false;
        }
    }

    return {
        level: maze.id.level,
        width: maze.id.width,
        height: maze.id.height,
        wallData: pako.inflate(compressed),
        path: path
    }
}

function mouseEnterCell(level, row, col) {
    var cellId = 'cell_' + level + '_' + row + '_' + col;
    $('#' + cellId).css('background-color', 'cyan');
}

function mouseExitCell(level, row, col) {
    var cellId = 'cell_' + level + '_' + row + '_' + col;
    $('#' + cellId).css('background-color', 'white');
}

function mouseClickCell(level, row, col) {
    var cellId = 'cell_' + level + '_' + row + '_' + col;
    $('#' + cellId).css('background-color', 'red');
}

function generateMazeRowHtml(maze, row) {
    var html = '';
    html += '<tr>';
    for (var col = 0; col < maze.width; col++) {
        var topWall = hasTopWall(row, col, maze);
        var bottomWall = hasBottomWall(row, col, maze);
        var leftWall = hasLeftWall(row, col, maze);
        var rightWall = hasRightWall(row, col, maze);

        styles = [];
        styles.push('width:' + cellSize + 'px;');
        styles.push('height:' + cellSize + 'px;');
        styles.push('font-size:1px;');
        styles.push('border-style:solid;');
        styles.push('border-top-width:' + (topWall ? '1px' : '0px') + ';');
        styles.push('border-bottom-width:' + (bottomWall ? '1px' : '0px') + ';');
        styles.push('border-left-width:' + (leftWall ? '1px' : '0px') + ';');
        styles.push('border-right-width:' + (rightWall ? '1px' : '0px') + ';');
        styles.push('border-color:black;');

        var cellId = 'cell_' + maze.level + '_' + row + '_' + col;

        html += '<td id="' + cellId + '"';
        html += '    style="' + styles.join('') + '"';
        html += '    onmouseover="javascript:mouseEnterCell(' + maze.level + ', ' + row + ', ' + col + ');"';
        html += '    onmouseout="javascript:mouseExitCell(' + maze.level + ', ' + row + ', ' + col + ');"';
        html += '    onclick="javascript:mouseClickCell(' + maze.level + ', ' + row + ', ' + col + ');">';
        html += '&nbsp;';
        html += '</td>';
    }
    html += '</tr>';
    return html;
}

function generateMazeHtml() {
    var html = '';
    html += '<table cellpadding="0" cellspacing="0">';
    for (var row = 0; row < currentMaze.height; row++) {
        html += generateMazeRowHtml(currentMaze, row);
    }
    html += '</table>';
    return html;
}

function generateInfoHtml() {
    var html = '';
    html += '<table cellpadding="0" cellspacing="0">';
    html += '  <tr>';
    html += '    <td style="padding-right:20px;font-family:Verdana;font-size:12pt;">';
    html += '      Level:';
    html += '    </td>';
    html += '    <td style="padding-right:80px;font-family:Verdana;font-size:12pt;">';
    html += '      ' + currentMaze.level;
    html += '    </td>';
    html += '    <td style="padding-right:20px;font-family:Verdana;font-size:12pt;">';
    html += '      Score:';
    html += '    </td>';
    html += '    <td style="padding-right:80px;font-family:Verdana;font-size:12pt;">';
    html += '      ' + score;
    html += '    </td>';
    html += '  </tr>';
    html += '</table>';
    return html;
}

$(document).ready(function() {
    $.ajax({
        url: '/api/maze/1',
        type: 'GET',
        success: function(maze) {
            currentMaze = processMaze(maze);

            widthRatio = Math.floor(window.innerWidth / currentMaze.width);
            heightRatio = Math.floor(window.innerHeight / currentMaze.height);
            cellSize = Math.min(widthRatio, heightRatio);

            $('#maze').html(generateMazeHtml());
            $('#info').html(generateInfoHtml());
        }
    });
});
