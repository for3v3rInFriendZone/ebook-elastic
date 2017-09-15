(function() {
	'use strict';

	angular
		.module('ebook-book')
		.controller('BookSearchController', BookSearchController);

	BookSearchController.$inject = ['localStorageService', '$state', 'books', 'categories', 'languages', 'Book'];
	function BookSearchController(localStorageService, $state, books, categories, languages, Book) {
		
		var bsc = this;	
		bsc.user = localStorageService.get('user');
		bsc.books = books;
		bsc.categories = categories;
		bsc.languages = languages;
		bsc.cancel = cancel;
		bsc.search = search;
		bsc.goToDetails = goToDetails;
		bsc.operators = [
			'AND', 'OR'
		];
		
		function cancel() {
			$state.go('main.listBook');
		}
		
		function goToDetails(bookId) {
			$state.go('main.userBook', {id: bookId});
		}
		
		function search() {
			var listOfSearches = [];
			if(bsc.bookDTOTitle != undefined && bsc.bookDTOTitle.value != null && bsc.bookDTOTitle.value != '') {
				bsc.bookDTOTitle.field = 'title';
				listOfSearches.push(bsc.bookDTOTitle);
			}
			
			if(bsc.bookDTOAuthor != undefined && bsc.bookDTOAuthor.value != null && bsc.bookDTOAuthor.value != '') {
				bsc.bookDTOAuthor.field = 'author';
				listOfSearches.push(bsc.bookDTOAuthor);
			}
			
			if(bsc.bookDTOKeywords != undefined && bsc.bookDTOKeywords.value != null && bsc.bookDTOKeywords != '') {
				bsc.bookDTOKeywords.field = 'keywords';
				listOfSearches.push(bsc.bookDTOKeywords);
			}
			
			if(bsc.bookDTOText != undefined && bsc.bookDTOText.value != null && bsc.bookDTOText != '') {
				bsc.bookDTOText.field = 'text';
				listOfSearches.push(bsc.bookDTOText);
			}
			
			if(bsc.bookDTOLanguage != undefined && bsc.bookDTOLanguage.value != null && bsc.bookDTOLanguage != '') {
				bsc.bookDTOLanguage.field = 'langauge';
				listOfSearches.push(bsc.bookDTOLanguage);
			}
			
			Book.search(listOfSearches).then(function(result){
				bsc.searchResults = result;
			});
		}
		
		
		
	}
})();
