(function() {
	'use strict';

	angular
		.module('ebook-category')
		.controller('CategoryListBookController', CategoryListBookController);

	CategoryListBookController.$inject = ['localStorageService', '$state', 'book', '$stateParams'];
	function CategoryListBookController(localStorageService, $state, book, $stateParams) {
		
		var clbc = this;	
		clbc.user = localStorageService.get('user');
		clbc.book = book;
		clbc.cancel = cancel;
		
		function cancel() {
			$state.go('main.categoryBooks', {id: $stateParams.id});;
		}
		
		
		
		
	}
})();
