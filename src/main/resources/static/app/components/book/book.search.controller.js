(function() {
	'use strict';

	angular
		.module('ebook-book')
		.controller('BookSearchController', BookSearchController);

	BookSearchController.$inject = ['localStorageService', '$state', 'books', 'categories', 'languages'];
	function BookSearchController(localStorageService, $state, books, categories, languages) {
		
		var bsc = this;	
		bsc.user = localStorageService.get('user');
		bsc.books = books;
		bsc.categories = categories;
		bsc.languages = languages;
		bsc.cancel = cancel;
		bsc.search = search;
		bsc.searchResults = [];
		
		function cancel() {
			$state.go('main.listBook');
		}
		
		function search() {
			
		}
		
		
		
	}
})();
