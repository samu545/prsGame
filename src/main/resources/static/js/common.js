app.service('Common', function() {
    var Common = {};
	
    Common.clearErrors = function(scope){ 
        scope.error = {};
    };
    
    Common.initErrors = function(scope, entity) {
    	scope.error = {};
    	scope.error[entity] = {};
    };
    
    Common.setError = function(scope, entity, field, message) {
    	var eEntity = scope.error[entity];
    	eEntity[field] = message;
    };
    
    Common.getError = function(scope, entity, field) {
    	var eEntity = scope.error[entity];
    	if (angular.isDefined(eEntity)
    			&& angular.isDefined(eEntity[field])) {
    		return eEntity[field];
    	} else {
    		return null;
    	}
    }
    
    Common.clearError = function(scope, entity, field) {
    	var eEntity = scope.error[entity];
    	if (angular.isDefined(eEntity)
    			&& angular.isDefined(eEntity[field])) {
    		delete eEntity[field];
    	}
    };
	
	return Common;
});