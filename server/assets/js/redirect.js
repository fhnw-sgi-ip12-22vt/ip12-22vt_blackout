setTimeout(() => {
    const params = new URLSearchParams(window.location.search);
    const redirectURL = params.get('redirectURL');

    if (redirectURL) {
        handleLocationChange(redirectURL);
    } else {
        handleLocationChange('/');
    }
}, 1000);