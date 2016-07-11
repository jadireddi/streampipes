 angular
    .module('streamPipesApp')
    .config(function($mdIconProvider) {

		$mdIconProvider
			.iconSet('action', '/semantic-epa-backend/img/svg/svg-sprite-action.svg', 24)
			.iconSet('alert', '/semantic-epa-backend/img/svg/svg-sprite-alert.svg', 24)
			.iconSet('av', '/semantic-epa-backend/img/svg/svg-sprite-av.svg', 24)
			.iconSet('communication', '/semantic-epa-backend/img/svg/svg-sprite-communication.svg', 24)
			.iconSet('content', '/semantic-epa-backend/img/svg/svg-sprite-content.svg', 24)
			.iconSet('device', '/semantic-epa-backend/img/svg/svg-sprite-device.svg', 24)
			.iconSet('editor', '/semantic-epa-backend/img/svg/svg-sprite-editor.svg', 24)
			.iconSet('file', '/semantic-epa-backend/img/svg/svg-sprite-file.svg', 24)
			.iconSet('hardware', '/semantic-epa-backend/img/svg/svg-sprite-hardware.svg', 24)
			.iconSet('image', '/semantic-epa-backend/img/svg/svg-sprite-image.svg', 24)
			.iconSet('maps', '/semantic-epa-backend/img/svg/svg-sprite-maps.svg', 24)
			.iconSet('navigation', '/semantic-epa-backend/img/svg/svg-sprite-navigation.svg', 24)
			.iconSet('notification', '/semantic-epa-backend/img/svg/svg-sprite-notification.svg', 24)
			.iconSet('social', '/semantic-epa-backend/img/svg/svg-sprite-social.svg', 24)
			.iconSet('toggle', '/semantic-epa-backend/img/svg/svg-sprite-toggle.svg', 24)
			.iconSet('avatars', '/semantic-epa-backend/img/svg/avatar-icons.svg', 24)
			.defaultIconSet('/semantic-epa-backend/img/svg/svg-sprite-action.svg', 24);
	})
	.config(["$httpProvider", function($httpProvider) {
		$httpProvider.defaults.withCredentials = true;
		$httpProvider.interceptors.push('httpInterceptor');
	}])
	.config(function($stateProvider, $urlRouterProvider) {

//	    $urlRouterProvider.otherwise( function($injector, $location) {
//            var $state = $injector.get("$state");
//            $state.go("streampipes");
//        });

		$urlRouterProvider.otherwise("/streampipes");

		$stateProvider
			.state('streampipes', {
				url: '/streampipes',
				views: {
					"top" : {
						templateUrl : "top.html",	
						controller: "TopNavCtrl"
					},
					"container" : {
						templateUrl : "streampipes.html",
						controller: 'AppCtrl'
					},
					"streampipesView@streampipes" : {
						templateUrl : "modules/editor/editor.html",
						controller: 'EditorCtrl',
						resolve:{
							'AuthData':function(authService){
								return authService.authenticate;
							}
						}
					}
				}
			})
			.state('streampipes.edit', {
				url: '/editor/:pipeline',
				views: {
					"top" : {
						templateUrl : "top.html",
		            	controller : "TopNavCtrl"
					},
					"container" : {
						templateUrl : "streampipes.html",
						controller: 'AppCtrl'
					},
					"streampipesView@streampipes" : {
						templateUrl : "modules/editor/editor.html",
						controller: 'EditorCtrl'
					}
				}
			})
			.state('sso', {
				url: '/sso/:target',
				views: {
					"container" : {
						templateUrl : 'sso.html',
						controller: 'SsoCtrl'
					}
				}
			})
			.state('ssosuccess', {
				url: '/ssosuccess',
				views: {
					"container" : {
						templateUrl : 'sso.html',
					}
				}
			})
			.state('streampipes.login', {
				url: '/login/:target',
				views: {
					"streampipesView@streampipes" : {
						templateUrl : 'login.html',
						controller: 'LoginCtrl'
					}
				}
			})
			.state('home', {
				url: '/home',
				views: {
					"top" : {
						templateUrl : "top.html",
		            	controller : "TopNavCtrl"
					},
					"container" : {
						templateUrl : "modules/proasense-home/home.html",
						controller: 'HomeCtrl'
					}
				}
			})
			.state('streampipes.pipelines', {
				url: '/pipelines/:pipeline',
				views: {
					"streampipesView@streampipes" : {
						templateUrl : 'modules/pipelines/pipelines.html',
						controller: 'PipelineCtrl'
					}
				}
			})
			.state('streampipes.visualizations', {
				url: '/visualizations',
				views: {
					"streampipesView@streampipes" : {
						templateUrl : 'modules/visualizations-new/visualizations-new.html',
						controller: 'VizCtrl'
					}
				}
			})
			.state('streampipes.dashboard', {
				url: '/dashboard',
				views: {
					"streampipesView@streampipes" : {
						templateUrl : 'modules/dashboard/dashboard.html',
						controller: 'DashCtrl'
					}
				}
			})
//			.state('streampipes.marketplace', {
//				url: '/marketplace',
//				views: {
//					"streampipesView@streampipes" : {
//						templateUrl : 'modules/marketplace/marketplace.html',
//						controller: 'MarketplaceCtrl'
//					}
//				}
//			})
			.state('streampipes.ontology', {
				url: '/ontology',
				views: {
					"streampipesView@streampipes" : {
						templateUrl : 'modules/ontology/ontology.html',
						controller: 'OntologyCtrl'
		            }
	            }
	          })
	          .state('streampipes.sensors', {
	            url: '/sensors',
	            views: {
		            "streampipesView@streampipes" : {
						templateUrl : 'modules/sensors/sensors.html',
						controller: 'SensorCtrl'
		            }
	            }
	          })
	          .state('streampipes.myelements', {
	            url: '/myelements',
	            views: {
		            "streampipesView@streampipes" : {
		            	  templateUrl : 'modules/myelements/myelements.html',
		            	  controller: 'MyElementsCtrl'
		            }
	            }
	          })
	          .state('streampipes.tutorial', {
	            url: '/tutorial',
	            views: {
		            "streampipesView@streampipes" : {
		            	  templateUrl : 'tutorial.html',
		            	  controller: 'AppCtrl'
		            }
	            }
	          })
	           .state('streampipes.register', {
	            url: '/register',
	            views: {
		            "streampipesView@streampipes" : {
		            	  templateUrl : 'register.html',
		            	  controller: 'RegisterCtrl'
		            }
	            }
	          })
	           .state('streampipes.add', {
	            url: '/add',
	            views: {
		            "streampipesView@streampipes" : {
		            	  templateUrl : 'modules/add/add.html',
		            	  controller: 'AddCtrl'
		            }
	            }
	          })
	          .state('streampipes.documentation', {
	            url: '/docs',
	            views: {
		            "streampipesView@streampipes" : {
		            	  templateUrl : 'modules/docs/docs.html',
		            	  controller: 'DocsController'
		            }
	            }
	          })
	          .state('streampipes.setup', {
	            url: '/setup',
	            views: {
		            "streampipesView@streampipes" : {
		            	  templateUrl : 'setup.html',
		            	  controller: 'SetupCtrl'
		            }
	            }
	          })
	          .state('streampipes.error', {
	            url: '/error',
	            views: {
		            "streampipesView@streampipes" : {
		            	  templateUrl : 'error.html',
		            	  controller: 'SetupCtrl'
		            }
	            }
	          })
	          .state('streampipes.settings', {
	            url: '/settings',
	            views: {
		            "streampipesView@streampipes" : {
		            	  templateUrl : 'settings.html',
		            	  controller: 'SettingsCtrl'
		            }
	            }
	          })
	          .state('streampipes.notifications', {
	            url: '/notifications',
	            views: {
		            "streampipesView@streampipes" : {
		            	  templateUrl : 'modules/notifications/notifications.html',
		            	  controller: 'RecommendationCtrl'
		            }
	            }
	          })
	           .state('streamstory', {
	            url: '/streamstory',
	            views: {
	            	 "top" : {
		            	  templateUrl : "top.html",
			              controller : "TopNavCtrl"
		              	},
		            "container" : {
		            	  templateUrl : 'streamstory.html',
		            	  controller: "TopNavCtrl"
		            }
	            }
	          })
	          .state('pandda', {        	  
	            url: '/pandda',
	            views: {
	            	 "top" : {
		            	  templateUrl : "top.html",
		            	  controller : "TopNavCtrl"
		              	},
		            "container" : {
		            	  templateUrl : 'pandda.html',
		            	  controller: "TopNavCtrl"
		            }
	            }
	          })
	          .state('hippo', {
	            url: '/hippo',
	            views: {
	            	 "top" : {
		            	  templateUrl : "top.html",
		            	  controller : "TopNavCtrl"
		              	},
		            "container" : {
		            	  templateUrl : 'hippo.html',
		            	  controller: "TopNavCtrl"
		            }
	            }
	          }).state('inspectionReport', {
		            url: '/inspection',
		            views: {
		            	 "top" : {
			            	  templateUrl : "top.html",
			            	  controller : "TopNavCtrl"
			              	},
			            "container" : {
			            	  templateUrl : 'humaninspectionreport.html',
			            	  controller: "TopNavCtrl"
			            }
		            }
	          }).state('maintenanceReport', {
		            url: '/maintenance',
		            views: {
		            	 "top" : {
			            	  templateUrl : "top.html",
			            	  controller : "TopNavCtrl"
			              	},
			            "container" : {
			            	  templateUrl : 'humanmaintenancereport.html',
			            	  controller: "TopNavCtrl"
			            }
		            }
		          })
	        
    });