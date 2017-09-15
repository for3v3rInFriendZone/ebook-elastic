(function() {
	"use strict";

	angular
		.module('ebook-user')
		.factory('User', User);

	User.$inject = ['Restangular'];
	function User(Restangular) {

		return {
			save: function(user, callback) {
				return Restangular.all('user').post(user).then(function(data) {
					callback();
				});
			},
			findAll: function() {
				return Restangular.all("user").getList();
			},
			findOne: function(userId) {
				return Restangular.one('user', userId).get();
			},
			remove: function(userId, callback) {
				return Restangular.one('user', userId).remove().then(function(data) {
					callback();
				});
			}
		};
	}
})();