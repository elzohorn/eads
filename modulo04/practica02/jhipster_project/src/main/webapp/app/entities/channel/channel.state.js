(function() {
    'use strict';

    angular
        .module('modulo04Practica02JhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('channel', {
            parent: 'entity',
            url: '/channel',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Channels'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/channel/channels.html',
                    controller: 'ChannelController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('channel-detail', {
            parent: 'entity',
            url: '/channel/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Channel'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/channel/channel-detail.html',
                    controller: 'ChannelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Channel', function($stateParams, Channel) {
                    return Channel.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'channel',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('channel-detail.edit', {
            parent: 'channel-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/channel/channel-dialog.html',
                    controller: 'ChannelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Channel', function(Channel) {
                            return Channel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('channel.new', {
            parent: 'channel',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/channel/channel-dialog.html',
                    controller: 'ChannelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('channel', null, { reload: 'channel' });
                }, function() {
                    $state.go('channel');
                });
            }]
        })
        .state('channel.edit', {
            parent: 'channel',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/channel/channel-dialog.html',
                    controller: 'ChannelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Channel', function(Channel) {
                            return Channel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('channel', null, { reload: 'channel' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('channel.delete', {
            parent: 'channel',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/channel/channel-delete-dialog.html',
                    controller: 'ChannelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Channel', function(Channel) {
                            return Channel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('channel', null, { reload: 'channel' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
