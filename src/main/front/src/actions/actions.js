// actions.js
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
    show:true
  });
  
  export const closeSignIn = show => ({
    type: "CLOSE_SIGN_IN",
    show
  });
  