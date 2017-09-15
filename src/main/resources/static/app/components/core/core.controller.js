(function() {
	'use strict';

	angular
		.module('ebook-core')
		.controller('CoreController', CoreController);

	CoreController.$inject = ['$location', '$anchorScroll', '$state', 'localStorageService'];
	function CoreController($location, $anchorScroll, $state, localStorageService) { 
		var ccr = this;
		ccr.goToSection = goToSection;
		
		if(localStorageService.get('admin') != null || localStorageService.get('admin') != undefined) {
			$state.go('user', {username: localStorageService.get('admin').username});
		}
		
		function goToSection(section) {
			
			$location.hash(section);
	  
		    $anchorScroll();
		}
		
		
		
	}
})();
