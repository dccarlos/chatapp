'use strict'
import { combineReducers } from 'redux';

export const geod = (state = {}, action) => {
    switch (action.type) {
        case 'ACTIVATE_GEOD': {
            console.log('acticate goed')
            return action.geod;
        }

        case 'CLOSE_GEOD':
            return {};
        default:
            return state;
    }
};
export const showModel = (state = { show: false }, action) => {
    switch (action.type) {
        case 'CLOSE_SIGN_IN': {
            return {show:false};
        }
        case 'SHOW_SIGN_IN': {
                return {show:true};
        }
        default:
            return state;
    }
};

export const reducers = combineReducers({
    geod,
    showModel
});


