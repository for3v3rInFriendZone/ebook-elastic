(function() {
	"use strict";

	angular
		.module('ebook-category')
		.factory('Category', Category);

	Category.$inject = ['Restangular'];
	function Category(Restangular) {
		
		return {
			save: function(category, callback) {
				return Restangular.all('category').post(category).then(function(data) {
					callback();
				});
			},
			findAll: function() {
				return Restangular.all('category').getList();
			},
			findOne: function(categoryId) {
				return Restangular.one('category', categoryId).get();
			},
			remove: function(categoryId, callback1, callback2) {
				return Restangular.one('category', categoryId).remove().then(function(data) {
					callback2();
					callback1();
				})
			}
		}
	}
})();