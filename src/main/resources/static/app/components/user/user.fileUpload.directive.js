(function() {
	'use strict';
	
	angular
	.module('ebook-user')
	.directive("fileread", fileread);
	
	fileread.$inject = ['Book', 'Restangular'];
	function fileread(Restangular) { 
	    return { 
	        scope: {
	            fileread: "="
	        },
	        link: function (scope, element, attributes) {
	            element.bind("change", function (changeEvent) {
	                var reader = new FileReader();
	                scope.file = changeEvent.target.files[0];
	                reader.onload = function (loadEvent) {
	                    scope.$apply(function () {
	                        var ebook = {};
	                        var fd = new FormData();
	                        fd.append('file', scope.file);
	                        Restangular.one('book/upload').withHttpConfig({transformRequest: angular.identity})
	                        .customPOST(fd, '', undefined, {'Content-Type': undefined})
	                        .then(function(info) {
	                        	ebook.id = info.id;
	                        	ebook.filename = info.filename;
	                        	ebook.author = info.author;
	                        	ebook.title = info.title;
	                        	ebook.keywords = info.keywords;
	                        	ebook.mime = info.mime;
	                        	scope.fileread = ebook;
	                        });
	                    });
	                }
	                reader.readAsDataURL(changeEvent.target.files[0]);
	            });
	        }
	    }
	};
})();