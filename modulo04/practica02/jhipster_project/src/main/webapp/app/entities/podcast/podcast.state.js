(function() {
    'use strict';

    angular
        .module('modulo04Practica02JhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('podcast', {
            parent: 'entity',
            url: '/podcast',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Podcasts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/podcast/podcasts.html',
                    controller: 'PodcastController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('podcast-detail', {
            parent: 'entity',
            url: '/podcast/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Podcast'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/podcast/podcast-detail.html',
                    controller: 'PodcastDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Podcast', function($stateParams, Podcast) {
                    return Podcast.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'podcast',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('podcast-detail.edit', {
            parent: 'podcast-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/podcast/podcast-dialog.html',
                    controller: 'PodcastDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Podcast', function(Podcast) {
                            return Podcast.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('podcast.new', {
            parent: 'podcast',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/podcast/podcast-dialog.html',
                    controller: 'PodcastDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('podcast', null, { reload: 'podcast' });
                }, function() {
                    $state.go('podcast');
                });
            }]
        })
        .state('podcast.edit', {
            parent: 'podcast',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/podcast/podcast-dialog.html',
                    controller: 'PodcastDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Podcast', function(Podcast) {
                            return Podcast.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('podcast', null, { reload: 'podcast' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('podcast.delete', {
            parent: 'podcast',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/podcast/podcast-delete-dialog.html',
                    controller: 'PodcastDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Podcast', function(Podcast) {
                            return Podcast.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('podcast', null, { reload: 'podcast' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
