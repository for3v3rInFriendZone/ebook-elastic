(function() {
	'use strict';

	angular
		.module('ebook-book')
		.controller('BookController', BookController);

	BookController.$inject = ['$scope', 'localStorageService', '$state', 'book', 'title', 'users', 'categories', 'languages', 'Book', 'Upload'];
	function BookController($scope, localStorageService, $state, book, title, users, categories, languages, Book, Upload) {
		
		var bcr = this;	
		
		bcr.book = book;
		bcr.bookUser = localStorageService.get('user');
		bcr.title = title;
		bcr.users = users;
		bcr.categories = categories;
		bcr.languages = languages;
		bcr.currentState = $state.current.name;
		bcr.cancel = cancel;
		bcr.done = done;
		bcr.remove = remove;
		bcr.downloadBook = downloadBook;
		bcr.downloadLink = 'file:///C:/Users/Marko/git/ebook-elastic/src/main/resources/static/assets/PdfStorage/' + bcr.book.filename;
		
		$scope.upload = function (file) {
	        Upload.upload({
	            url: '/book/upload',
	            data: {file: file}
	        }).then(function (resp) {
	            console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
	            bcr.book = resp.data;
	        }, function (resp) {
	            console.log('Error status: ' + resp.status);
	        }, function (evt) {
	            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
	            console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
	        });
	    };
		
		if(bcr.book.id != null && bcr.book.id != undefined) {
			bcr.edit = true;
		} 
		
		function cancel() {
			$state.go('main.listBook');
		}
		
		function done() {
			bcr.submitted = true;
			if(bcr.form.$invalid) {
				return;
			}
			if(bcr.book.image == null || bcr.book.image == undefined || bcr.book.image == '') {
				bcr.book.image = 'http://psicoterapeutas.eu/imagenes-psicoterapeutas-eu/Photoxpress_4839887.jpg';
			}
			if(bcr.edit) {
				Book.edit(bcr.book, cancel);
			} else {
				Book.save(bcr.book, cancel);
			}
			
		}
		
		function remove() {
			Book.remove(bcr.book.id, successRemoveModal);
		}
		
		function successRemoveModal() {
			alert('Book with id: '+ bcr.book.id + ' has been successfully removed.');
			$state.go('main.listBook');
		}
		
		function downloadBook(bookId) {
			Book.download(bookId).then(function(response) {
				
				var anchor = angular.element('<a/>');
		        anchor.attr({
		            href: 'data:application/octet-stream;base64,' + response,
		            target: '_self',
		            download: bcr.book.title + ".pdf"       
		            });

		        angular.element(document.body).append(anchor);
		        anchor[0].click();
			});
		}
	}
})();
