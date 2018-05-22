/**
 * 
 */
'use strict';
 
angular.module('myApp').factory('UserService', ['$http', '$q', function($http, $q){
 
	//this is the REST SERVICE URI were all the controllers in UserRestController.java have in the
	//request mapping, some controllers are get and some post and some have only this URI and some others
	//have extra parameter appended to the URI
    var REST_SERVICE_URI = 'http://localhost:8080/WebTimeSheetCalculator/userStatisticsRest/';
 
    var factory = {
        fetchAllUsers: fetchAllUsers,
        createUser: createUser,
        updateUser:updateUser,
        deleteUser:deleteUser
    };
 
    return factory;
 
    //this function will trigger the http://localhost:8080/WebTimeSheetCalculator/userStatisticsRest/ request mapping
    //GET controller which is the listAllUsers function (get method)
    function fetchAllUsers() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching Users');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
 
    //this function will trigger the http://localhost:8080/WebTimeSheetCalculator/userStatisticsRest/  request mapping
    //POST controller which is the createUser function and has in the RequestBody the user object
    function createUser(user) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, user)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while creating User');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
 
    //this function will trigger the  http://localhost:8080/WebTimeSheetCalculator/userStatisticsRest/{id} request mapping
    //PUT controller which is the updateUser(@PathVariable("id") long id, @RequestBody User user) 
    function updateUser(user, id) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI+id, user)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while updating User');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
    //this function will trigger the http://localhost:8080/WebTimeSheetCalculator/userStatisticsRest/{id} request mapping
    //DELETE controller which is the deleteUser(@PathVariable("id") long id)
    function deleteUser(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI+id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while deleting User');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
 
}]);