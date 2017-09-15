(function() {
	"use strict";

	angular
		.module('ebook-core')
		.config(config);


	config.$inject = ['$urlRouterProvider', '$stateProvider'];
	function config($urlRouterProvider, $stateProvider){

		$stateProvider
		.state("main.listCategory", {
			url: '/categories',
			views:{
				'main@': {
					resolve: {
						categories: getCategories	
					},
					templateUrl: "app/components/category/category.list.html",
					controller: "CategoryListController",
					controllerAs: "clc"
				}
			}
		})
		.state("main.editCategory", {
			url: '/category/:id',
			views:{
				'main@': {
					resolve: {
						category: editCategory,
						title: editTitle,
						books: getBooks
					},
					templateUrl: "app/components/category/category.html",
					controller: "CategoryController",
					controllerAs: "ccr"
				}
			}
		})
		.state("main.newCategory", {
			url: '/categories/new',
			views:{
				'main@': {
					resolve: {
						category: newCategory,
						title: newTitle, 
						books: getBooks
					},
					templateUrl: "app/components/category/category.html",
					controller: "CategoryController",
					controllerAs: "ccr"
				}
			}
		})
		.state("main.categoryBooks", {
			url: '/categorie/:id/books',
			views:{
				'main@': {
					resolve: {
						category: editCategory,
						title: categoryBooksTitle,
						books: getBooks
					},
					templateUrl: "app/components/category/category.listOfBooks.html",
					controller: "CategoryController",
					controllerAs: "ccr"
				}
			}
		})
		.state("main.bookPreviewCategory", {
			url: '/categorie/:id/book/:id2',
			views:{
				'main@': {
					resolve: {
						book: showBook
					},
					templateUrl: "app/components/category/category.bookUser.html",
					controller: "CategoryListBookController",
					controllerAs: "clbc"
				}
			}
		});
		
		getBooks.$inject = ['Book'];
		function getBooks(Book) {
			return Book.findAll();
		}
		
		getCategories.$inject = ['Category'];
		function getCategories(Category) {
			return Category.findAll();
		}
		
		function newCategory() {
			return {};
		}
		
		editCategory.$inject = ['$stateParams', 'Category'];
		function editCategory($stateParams, Category) {
			return Category.findOne($stateParams.id);
		}
		
		function newTitle() {
			return "New category";
		}
		
		
		function categoryBooksTitle() {
			return "";
			
		}
		
		editTitle.$inject = ['$stateParams'];
		function editTitle($stateParams) {
			return "Edit category with id " + $stateParams.id;
		}
		
		showBook.$inject = ['$stateParams', 'Book'];
		function showBook($stateParams, Book) {
			return Book.findOne($stateParams.id2);
		}
	}
})();
