(function() {
	'use strict';

	angular
		.module('ebook-book')
		.controller('BookListController', BookListController);

	BookListController.$inject = ['localStorageService', '$state', 'books'];
	function BookListController(localStorageService, $state, books) {
		
		var blc = this;	
		blc.user = localStorageService.get('user');
		blc.books = books;
		blc.newBook = newBook;
		blc.editBook = editBook;
		blc.searchBook = searchBook;
		
		/**
		 * If user is subscriber and have a defined category, he can be shown only books from his category
		 
		if(blc.user != null && blc.user != undefined) {
			if(blc.user.type == 'subscriber' && (blc.user.category != null && blc.user.category != undefined)){
				blc.subBooks = [];
				for(var i=0; i<blc.books.length; i++) {
					if(blc.books[i].category.id == blc.user.category.id) {
						blc.subBooks.push(blc.books[i]);
					}
				}
				blc.books = blc.subBooks;
			}	
		}
		*/
		
		function newBook() {
			$state.go('main.newBook');
		}
		
		function editBook(id) {
			if(blc.user) {
				if(blc.user.type == 'admin') {
					$state.go('main.editBook', {id: id});
				} else if(blc.user.type == 'subscriber') {
					$state.go('main.userBook', {id: id});
				}
			} else {
				$state.go('main.userBook', {id: id});
			}
		}
		
		function searchBook() {
			$state.go('main.searchBook');
		}
		
	}
})();
