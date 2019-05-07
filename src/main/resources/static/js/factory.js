/**
 * 
 */
app.factory('Factory', ['$http', function($http) {
    var Factory = {};
    var gameRef = 'game/';
	
    Factory.makeTurn = function(id, playerThrow) {
    	return $http.post(gameRef + id, playerThrow);
    }
	
	return Factory;
}]);