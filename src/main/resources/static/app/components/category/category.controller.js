(function() {
	'use strict';

	angular
		.module('ebook-category')
		.controller('CategoryController', CategoryController);

	CategoryController.$inject = ['localStorageService', '$state', 'category', 'title', 'books', '$stateParams', 'Category'];
	function CategoryController(localStorageService, $state, category, title, books, $stateParams, Category) {
		
		var ccr = this;	
		
		ccr.category = category;
		ccr.books = [];
		ccr.title = title
		ccr.currentState = $state.current.name;
		ccr.cancel = cancel;
		ccr.done = done;
		ccr.remove = remove;
		ccr.anotherCategory = anotherCategory;
		ccr.bookUser = bookUser;
		
		/**
		 * Books for a selected category
		 */
		for(var i=0; i<books.length; i++) {
			if(books[i].category.id === ccr.category.id) {
				ccr.books.push(books[i]);
			}
		}
		
		/**
		 * Title for a selected category and there books
		 */
		if(ccr.currentState == 'main.categoryBooks') {
			ccr.title = "Books from " + ccr.category.name + " category";
		}
		
		function cancel() {
			$state.go('main.listCategory');
		}
		
		function done() {
			ccr.submitted = true;
			if(ccr.form.$invalid) {
				return;
			}
			
			Category.save(ccr.category, cancel);
		}
		
		function remove() {
			Category.remove(ccr.category.id, successRemoveModal, anotherCategory);
		}
		
		function anotherCategory() {
			ccr.anotherCategoryFlag = true;
			return;
		}
		
		function successRemoveModal() {
			alert('Category with id: '+ ccr.category.id + ' has been successfully removed.');
			$state.go('main.listCategory');
		}
		
		function bookUser(bookId){
			$state.go('main.bookPreviewCategory', {id: $stateParams.id, id2: bookId});
		}
		
	}
})();
