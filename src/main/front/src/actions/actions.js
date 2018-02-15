'use strict'
export const activateGeod = geod => ({
    type: "ACTIVATE_GEOD",
    geod
  });
  
  export const closeGeod = () => ({
    type: "CLOSE_GEOD"
  });
  
  ///
  export const showSignIn = () => ({
    type: "SHOW_SIGN_IN",
  });
  
  export const closeSignIn = () => ({
    type: "CLOSE_SIGN_IN",
  });



  