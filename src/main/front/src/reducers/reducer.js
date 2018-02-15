//reducer.js
import { combineReducers } from 'redux';

// reducer.js
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
            console.log('close')
            return {show:true};
        }
        case 'SHOW_SIGN_IN':
            {
                console.log('show')
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


