(function() {
	"use strict";

	angular
		.module('ebook-book')
		.factory('Language', Language);

	Language.$inject = ['Restangular'];
	function Language(Restangular) {

		return {
			findAll: function() {
				return Restangular.all('language').getList();
			}
		};
	}
})();