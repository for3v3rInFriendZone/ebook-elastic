(function() {
	'use strict';
	
	angular
	.module('ebook-user')
	.directive("imageread", [function () {
	    return {
	        scope: {
	        	imageread: "="
	        },
	        link: function (scope, element, attributes) {
	            element.bind("change", function (changeEvent) {
	                var reader = new FileReader();
	                reader.onload = function (loadEvent) {
	                    scope.$apply(function () {
	                        scope.imageread = loadEvent.target.result;                        
	                    });
	                }
	                reader.readAsDataURL(changeEvent.target.files[0]);
	            });
	        }
	    }
	}]);
})();