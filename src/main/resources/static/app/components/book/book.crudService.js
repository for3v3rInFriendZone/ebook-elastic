(function() {
	"use strict";

	angular
		.module('ebook-book')
		.factory('Book', Book);

	Book.$inject = ['Restangular'];
	function Book(Restangular) {

		return {
			save: function(book, callback) {
				return Restangular.all('book').post(book).then(function(data) {
					callback();
				});
			},
			edit: function(book, callback) {
				var restangularObj = Restangular.one('book', book.id);
				_.extend(restangularObj, book);
				return restangularObj.put().then(function(data) {
					callback();
				});
			},
			findAll: function() {
				return Restangular.all('book').getList();
			},
			findOne: function(bookId) {
				return Restangular.one('book', bookId).get();
			},
			remove: function(bookId, callback) {
				return Restangular.one('book', bookId).remove().then(function(data) {
					callback();
				});
			},
			upload: function(file) {
				return Restangular.all('book/upload').post(file);
			}
		};
	}
})();