(function() {
	"use strict";

	angular
		.module('ebook-book')
		.config(config);


	config.$inject = ['$urlRouterProvider', '$stateProvider'];
	function config($urlRouterProvider, $stateProvider){

		$stateProvider
		.state("main.editBook", {
			url: '/book/:id',
			views:{
				'main@': {
					resolve: {
						book: editBook,
						title: editTitle,
						users: getUsers,
						categories: getCategories,
						languages: getLanguages
					},
					templateUrl: "app/components/book/book.html",
					controller: "BookController",
					controllerAs: "bcr"
				}
			}
		})
		.state("main.userBook", {
			url: '/book-preview/:id',
			views:{
				'main@': {
					resolve: {
						book: editBook,
						title: editTitle,
						users: getUsers,
						categories: getCategories,
						languages: getLanguages
					},
					templateUrl: "app/components/book/bookUser.html",
					controller: "BookController",
					controllerAs: "bcr"
				}
			}
		})
		.state("main.searchBook", {
			url: '/books/search',
			views:{
				'main@': {
					resolve: {
						books: getBooks,
						categories: getCategories,
						languages: getLanguages
					},
					templateUrl: "app/components/book/book.search.html",
					controller: "BookSearchController",
					controllerAs: "bsc"
				}
			}
		})
		.state("main.newBook", {
			url: '/books/new',
			views:{
				'main@': {
					resolve: {
						book: newBook,
						title: newTitle,
						users: getUsers,
						categories: getCategories,
						languages: getLanguages
					},
					templateUrl: "app/components/book/book.html",
					controller: "BookController",
					controllerAs: "bcr"
				}
			}
		});
		
		newBook.$inject = ['Book'];
		function newBook(Book) {
			return {};
		}
		
		editBook.$inject = ['$stateParams', 'Book'];
		function editBook($stateParams, Book) {
			return Book.findOne($stateParams.id);
		}
		
		function newTitle() {
			return "New Book";
		}
		
		editTitle.$inject = ['$stateParams'];
		function editTitle($stateParams) {
			return "Edit book";
		}
		
		getUsers.$inject = ['User'];
		function getUsers(User) {
			return User.findAll();
		}
		
		getBooks.$inject = ['Book'];
		function getBooks(Book) {
			return Book.findAll();
		}
		
		getCategories.$inject = ['Category'];
		function getCategories(Category) {
			return Category.findAll();
		}
		
		getLanguages.$inject = ['Language'];
		function getLanguages(Language) {
			return Language.findAll();
		}
	}
})();
