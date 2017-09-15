(function() {
	'use strict';

	angular
		.module('ebook-category')
		.controller('CategoryListController', CategoryListController);

	CategoryListController.$inject = ['localStorageService', '$state', 'categories'];
	function CategoryListController(localStorageService, $state, categories) {
		
		var clc = this;	
		clc.user = localStorageService.get('user');
		clc.categories = categories;
		clc.newCategory = newCategory;
		clc.editCategory = editCategory;
		
		function newCategory() {
			$state.go('main.newCategory');
		}
		
		function editCategory(id) {
			if(clc.user.type == 'admin') {
				$state.go('main.editCategory', {id: id});
			} else if(clc.user.type == 'subscriber') {
				$state.go('main.categoryBooks', {id: id});
			}
			
		}
		
	}
})();
