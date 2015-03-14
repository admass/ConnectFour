/* global angular */

var app = angular.module('connectFour', ['ngRoute']);
app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.otherwise({
            redirectTo: '/home',
            controller: 'ConnectFourCtrl'
        });
    }]);
app.controller('ConnectFourCtrl', function ($scope, $http) {

    var emptyBoard =
            [[0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0]];

    $scope.players = [
        {
            id: 1,
            isBot: false,
            wonGames: 0
        },
        {
            id: 2,
            isBot: false,
            wonGames: 0
        }];

    $scope.tieGames = 0;
    $scope.whoWon = 0;
    $scope.currentPlayer = 1;
    $scope.data = copy(emptyBoard);
    $scope.finished = false;
    $scope.moveValues = [0, 0, 0, 0, 0, 0, 0];

    var nextPlayer = function () {
        $scope.currentPlayer = $scope.currentPlayer % 2 + 1;
    };

    $scope.resetBoard = function () {
        $scope.currentPlayer = 1;
        $scope.data = copy(emptyBoard);
        $scope.finished = false;
        if ($scope.players[0].isBot) {
            makeMoveBot();
        }
    };

    $scope.pushBot = function () {
        if (isBot($scope.currentPlayer))
            makeMoveBot();
    };

    $scope.makeMove = function (column) {
        if (isMoveValid(column)) {
            var row = getFirstFreeRowInColumn(column);
            $scope.data[row][column] = $scope.currentPlayer;
            checkIfWin();
            nextPlayer();
            if (isBot($scope.currentPlayer)) {
                makeMoveBot();
            } else {
                getMoveValues();
            }
        }
    };

    function checkIfWin() {
        var victory = hasVictory();
        if (victory === -1) {
            $scope.tieGames++;
            $scope.finished = true;
            $scope.whoWon = -1;
        } else if (victory === 1) {
            $scope.players[0].wonGames++;
            $scope.finished = true;
            $scope.whoWon = 1;
        } else if (victory === 2) {
            $scope.players[1].wonGames++;
            $scope.finished = true;
            $scope.whoWon = 2;
        }
        if (isBot(2) && isBot(1) && $scope.finished) {
            $scope.resetBoard();
            makeMoveBot();
        }
    }

    function isMoveValid(column) {
        return $scope.data[0][column] === 0 && !$scope.finished;
    }

    function getFirstFreeRowInColumn(column) {
        for (var i = $scope.data.length - 1; i >= 0; i--) {
            if ($scope.data[i][column] === 0) {
                return i;
            }
        }
    }

    function isBot(player) {
        return $scope.players[player - 1].isBot;
    }

    function makeMoveBot() {
        $http.post('http://localhost:8080/heuristic/postMove/'
                + $scope.currentPlayer , $scope.data).
                success(function (data) {
                    setTimeout($scope.makeMove(data.move),100);
                });
    }

    function getMoveValues() {
        $http.post('http://localhost:8080/heuristic/postMove/'
                + $scope.currentPlayer, $scope.data).
                success(function (data) {
                    $scope.moveValues = data.moveValues;
                });
    }

    function rows() {
        return $scope.data.length;
    }


    function columns() {
        return $scope.data[0].length;
    }


    function copy(arr) {
        var new_arr = arr.slice(0);
        for (var i = new_arr.length; i--; )
            if (new_arr[i] instanceof Array)
                new_arr[i] = copy(new_arr[i]);
        return new_arr;
    }

    function valueAt(row, column) {
        if (row < 0 || row >= rows() || column < 0 || column >= columns())
            return 0;
        return $scope.data[row][column];
    }

    function sum(fromRow, fromCol, rowDir, colDir, player) {

        var result = 0;
        var i = fromRow, j = fromCol;
        while (valueAt(i, j) === player) {
            i += rowDir;
            j += colDir;
            result += 1;
        }
        return result;
    }

    function isTie() {
        for (var i = 0; i < columns(); i++) {
            if (valueAt(0, i) === 0)
                return false;
        }
        return true;
    }

    function hasVictory() {
        for (var i = 0; i < rows(); i++) {
            for (var j = 0; j < columns(); j++) {
                if (isVictoryField(i, j)) {
                    return valueAt(i, j);
                }
            }
        }
        if (isTie())
            return -1;
        return 0;
    }

    function isVictoryField(row, column) {
        var playerAt = valueAt(row, column);
        return (playerAt !== 0) && (sumHorizontal(row, column, playerAt) >= 3
                || sumVertical(row, column, playerAt) >=  3
                || sumLeftTop(row, column, playerAt) >=  3
                || sumRightTop(row, column, playerAt) >=  3);
    }

    function sumRightTop(row, column, player) {
        return sum(row - 1, column + 1, -1, 1, player) + sum(row + 1, column - 1, 1, -1, player);
    }

    function sumLeftTop(row, column, player) {
        return sum(row - 1, column - 1, -1, -1, player) + sum(row + 1, column + 1, 1, 1, player);
    }

    function sumVertical(row, column, player) {
        return sum(row - 1, column, -1, 0, player) + sum(row + 1, column, 1, 0, player);
    }

    function sumHorizontal(row, column, player) {
        return sum(row, column + 1, 0, 1, player) + sum(row, column - 1, 0, -1, player);
    }

});
