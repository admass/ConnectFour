var app = angular.module('selectFour', ['ngRoute']);

app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.otherwise({
            redirectTo: '/home',
            controller: 'SelectFourCtrl'
        });
    }]);

app.controller('SelectFourCtrl', function ($scope, $http) {
    
    var emptyBoard =
            [[0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0]];

    $scope.player = 1;

    $scope.data = copy(emptyBoard);
    $scope.won = false;

    var nextPlayer = function () {
        $scope.player = ($scope.player % 2) + 1;
    };

    $scope.resetBoard = function () {
        $scope.player = 1;
        $scope.data = copy(emptyBoard);
    };

    $scope.makeMove = function (column) {
        getMove();
        if ($scope.data[0][column] === 0 && !$scope.won) {
            for (var i = $scope.data.length - 1; i >= 0; i--) {
                if ($scope.data[i][column] === 0) {
                    $scope.data[i][column] = $scope.player;
                    if (checkIfWin(i, column)) {
                        $scope.won = true;
                        break;
                    }
                    nextPlayer();
                    break;
                }
            }
        }
    };
    
    function getMove() {
    $http.post('http://localhost:8080/heuristic/postMove/1/random', $scope.data).
        success(function(data) {
            console.log(data);
        });
}

    function checkIfWin(row, column) {
        return ((sumTop(row, column) + sumBottom(row, column)) >= 3) ||
                ((sumLeft(row, column) + sumRight(row, column)) >= 3) ||
                ((sumTopLeft(row, column) + sumBottomRight(row, column)) >= 3) ||
                ((sumTopRight(row, column) + sumBottomLeft(row, column)) >= 3);
    }

    function isOwner(row, column) {
        return $scope.data[row][column] === $scope.player;
    }


    function rows() {
        return $scope.data.length;
    }


    function cols() {
        return $scope.data[0].length;
    }


    function copy(arr) {
        var new_arr = arr.slice(0);
        for (var i = new_arr.length; i--; )
            if (new_arr[i] instanceof Array)
                new_arr[i] = copy(new_arr[i]);
        return new_arr;
    }

    function sumTop(row, column) {
        var sum = 0;
        for (var i = row - 1; i >= 0; i--) {
            if (isOwner(i, column)) {
                sum = sum + 1;
            } else {
                break;
            }
        }
        return sum;
    }


    function sumBottom(row, column) {
        var sum = 0;
        for (var i = row + 1; i < rows(); i++) {
            if (isOwner(i, column)) {
                sum = sum + 1;
            } else {
                break;
            }
        }
        return sum;
    }

    function sumLeft(row, column) {
        var sum = 0;
        for (var i = column - 1; i >= 0; i--) {
            if (isOwner(row, i)) {
                sum = sum + 1;
            } else {
                break;
            }
        }
        return sum;
    }

    function sumRight(row, column) {
        var sum = 0;
        for (var i = column + 1; i < cols(); i++) {
            if (isOwner(row, i)) {
                sum = sum + 1;
            } else {
                break;
            }
        }
        return sum;
    }

    function sumTopLeft(row, column) {
        var sum = 0, i, j;
        for (i = row - 1, j = column - 1; i >= 0 && j >= 0; i--, j--) {
            if (isOwner(i, j)) {
                sum = sum + 1;
            } else {
                break;
            }
        }
        return sum;
    }

    function sumTopRight(row, column) {
        var sum = 0, i, j;
        for (i = row - 1, j = column + 1; i >= 0 && j < cols(); i--, j++) {
            if (isOwner(i, j)) {
                sum = sum + 1;
            } else {
                break;
            }
        }
        return sum;
    }

    function sumBottomLeft(row, column) {
        var sum = 0, i, j;
        for (i = row + 1, j = column - 1; i < rows() && j >= 0; i++, j--) {
            if (isOwner(i, j)) {
                sum = sum + 1;
            } else {
                break;
            }
        }
        return sum;
    }

    function sumBottomRight(row, column) {
        var sum = 0, i, j;
        for (i = row + 1, j = column + 1; i < rows() && j < cols(); i++, j++) {
            if (isOwner(i, j)) {
                sum = sum + 1;
            } else {
                break;
            }
        }
        return sum;
    }
});
