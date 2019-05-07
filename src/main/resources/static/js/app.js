/**
 * 
 */
var app = angular.module('app', ['ngRoute', 'ngResource', 'ui.bootstrap']);
app.config(['$locationProvider', function($locationProvider) {
	$locationProvider.hashPrefix('');
	}]);
app.config(function($routeProvider){
    $routeProvider
        .when('/game/',{
            templateUrl: '/views/game.html',
            controller: 'gameController'
        })
        .otherwise(
            { redirectTo: '/game'}
        );
});