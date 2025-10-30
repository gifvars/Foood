var authToken;

function showLoadIndication() {
    const loadIndicator = document.getElementById("loadIndicator");
    loadIndicator.hidden = false;
    const loadSpan = document.getElementById("loadSpan");
    loadSpan.hidden = false;
}

function hideLoadIndication() {
    const loadIndicator = document.getElementById("loadIndicator");
    loadIndicator.hidden = true;
    const loadSpan = document.getElementById("loadSpan");
    loadSpan.hidden = true;
}

function getIndexPage() {
    return `<h1>Welcome!</h1><p>This is the front page of the Foood food deliveries customer site.</p>`
}

function getAboutPage() {
    return `<h1>About Us</h1><p>Read everything about Foood right here!</p>`
}

function getRegisterPage() {
    return `<h1>Register as a New Customer</h1><p>You can register as a new customer for ordering from Foood here.</p>
    <p hidden="hidden" style="background: red; color: white" id="registerError"></p>
    <form id="registerForm">
            <label for="firstName">First name:</label><br>
            <input type="text" id="firstName" name="firstName"><br>
            <label for="lastName">Last name:</label><br>
            <input type="text" id="lastName" name="lastName"><br>
            <input type="text" id="userType" value="CUSTOMER" hidden="hidden">
            <label for="email">E-mail address:</label><br>
            <input type="email" id="email" name="email"><br>
            <label for="password">Password:</label><br>
            <input type="password" id="password" name="password"><br><br>
            <input type="submit" value="Submit">
    </form>
    <span id="loadSpan" hidden="hidden">Working...</span><img id="loadIndicator" hidden="hidden" src="https://cdnl.iconscout.com/lottie/premium/thumb/loader-animated-icon-download-in-lottie-json-gif-static-svg-file-formats--loading-progress-bar-circle-pack-emoji-icons-4574104.gif" alt="Animated Loading Gif Transparent Background Quarter Circle Loading" class=" nofocus" tabindex="0" aria-label="Animated Loading Gif Transparent Background Quarter Circle Loading" style="width: 50px; height: 50px">`
}

function attachRegisterHandler() {
    const form = document.getElementById("registerForm");
    form.addEventListener("submit", async function (e) {
        e.preventDefault(); // Prevent browser default submission
        showLoadIndication();

        const request = {
            firstName: document.getElementById("firstName").value,
            lastName: document.getElementById("lastName").value,
            userType: document.getElementById("userType").value,
            email: document.getElementById("email").value,
            password: document.getElementById("password").value,
        };

        try {
            const response = await registerSubmit(request);
            console.log("Registration success: ", response);
            registerResult(response);
        } catch (err) {
            console.error("Registration failed: ", err);
            const errorDiv = document.getElementById("registerError");
            errorDiv.innerText = err;
            errorDiv.hidden = false;
            hideLoadIndication();
        }
    })
}

async function registerSubmit(request) {
    const response = await fetch("http://127.0.0.1:8089/register", {
        method: "POST",
        body: JSON.stringify(request),
        headers: { "Content-type": "application/json" },
    });
    
    const result = await response.json();

    if (!response.ok) {
        const message = await JSON.parse(result.message.match("\"(.*)\"$")[1]).message;
        throw new Error("Failed to register: " + message);
    }

    return result;
}

function registerResult(result) {
    if (result) {
        const appDiv = document.getElementById('app');
        const htmlString = `<h1>New user successfully registered!</h1>
                            <p>First name: ${result.firstName}</p>
                            <p>Last name: ${result.LastNane}</p>
                            <p>Email: ${result.email}</p>`;
        appDiv.innerHTML = htmlString;
    }
}

function getSignInPage() {
    return `<h1>Sign In to Foood</h1>
    <p>Sign in as a customer to Foood here.</p>
    <p hidden="hidden" style="background: red; color: white" id="signInError"></p>
    <form id="signInForm">
        <label for="email">E-mail address:</label><br>
        <input id="email" type="email" name="email"><br>
        <label for="password">Password:</label><br>
        <input id="password" type="password", name="password"><br>
        <input type="submit" value="Submit">
    </form>
    <span id="loadSpan" hidden="hidden">Working...</span><img id="loadIndicator" hidden="hidden" src="https://cdnl.iconscout.com/lottie/premium/thumb/loader-animated-icon-download-in-lottie-json-gif-static-svg-file-formats--loading-progress-bar-circle-pack-emoji-icons-4574104.gif" alt="Animated Loading Gif Transparent Background Quarter Circle Loading" class=" nofocus" tabindex="0" aria-label="Animated Loading Gif Transparent Background Quarter Circle Loading" style="width: 50px; height: 50px">`
}

function attachSignInHandler() {
    const form = document.getElementById("signInForm");
    form.addEventListener("submit", async function (e) {
        e.preventDefault();
        showLoadIndication();

        const request = {
            email: document.getElementById("email").value,
            password: document.getElementById("password").value,
        }

        try {
            const response = await signInSubmit(request);
            console.log("Sign in success: ", response);
            state.access_token = response.access_token;
            state.refresh_token = response.refresh_token;
            state.token_expires += response.expires_in;
            state.refresh_expires += response.refresh_expires_in;
            const appDiv = document.getElementById('app');
            const htmlString = `<h1>Signed In!</h1>
                                <p>Good news! You successfully signed in!</p>`;
            appDiv.innerHTML = htmlString;
        } catch (err) {
            console.error("Sign in failed: ", err);
            const errorDiv = document.getElementById("signInError");
            errorDiv.innerText = err;
            errorDiv.hidden = false;
            hideLoadIndication();
        }
    })
}

async function signInSubmit(request) {
    const response = await fetch("http://127.0.0.1:8089/signIn", {
        method: "POST",
        body: JSON.stringify(request),
        headers: { "Content-type": "application/json" },
    });
    
    const result = await response.json();

    if (!response.ok) {
        const message = await JSON.parse(result.message.match("\"(.*)\"$")[1]).message;
        throw new Error("Failed to sign in: " + message);
    }

    return result;
}

function getProtectedPage() {
    return `<h1>Protected Page</h1>
<p>If you can see this page, your authentication has been successful.</p>`;
}

const routes = {
    '#/': getIndexPage,
    '#/about': getAboutPage,
    '#/register': getRegisterPage,
    '#/signIn': getSignInPage,
    '#/protected': getProtectedPage,
};

const publicPaths = {
    '#/': 'root',
    '#/about': 'about',
    '#/register': 'register',
    '#/signIn': 'signIn',
}

const handlers = {
    '#/register': attachRegisterHandler,
    '#/signIn': attachSignInHandler,
}

window.addEventListener('hashchange', renderContent);
window.addEventListener('DOMContentLoaded', renderContent);

const state = {
    currentPage: 'home',
    isLoading: false,
    access_token: '',
    refresh_token: '',
    token_expires: Date.now() / 1000,
    refresh_expires: Date.now() / 1000,
};

function updateState(newState) {
    Object.assign(state, newState);
    renderContent();
}

async function renderContent() {
    const appDiv = document.getElementById('app');
    if (state.isLoading) {
        appDiv.innerHTML = '<div>Loading...</div>';
        return;
    }
    const hash = window.location.hash || '#/';
    const routeFn = routes[hash];
    const validAuth = await enforceAuthorization(hash);
    if (validAuth) {
        // TODO: Short-circuit auth check if the page isn't found (the 404 page should be public).
        appDiv.innerHTML = routeFn ? routeFn() : "<h1>Page not found</h1>";
    
        const handlerFn = handlers[hash];
        if (handlerFn) {
            handlerFn();
        }
    }
}

function denyAccess() {
    window.location.hash = '#/signIn';
    return false;
}

async function enforceAuthorization(hash) {

    if (hash in publicPaths) {
        return true;
    }

    // If no access_token, redirect to login.
    if (!state.access_token || state.access_token.length === 0) {
        if (window.location.hash !== '#/signIn') {
            denyAccess();
            return false;
        }
    }

    // 2. Check access_token validity. If valid, run it past Keycloak to confirm, return true if accepted.
    const token_valid = await validateToken();
    if (token_valid) {
        return true;
    }

    // 3. If expired, use refresh_token to get a new token.
    try {
        await refreshToken();
        return true;
    } 
    // TODO: 4. If refresh_token has expired or an error occurs, redirect to login.
    catch (err) {
        console.error(err);
        denyAccess();
        return false;
    }
}

async function validateToken() {
    if (Date.now() / 1000 > state.token_expires) {
        return false;
    }
    const tokenResponse = await fetch("http://127.0.0.1:8089/validateToken", {
        method: "POST",
        body: JSON.stringify({ token: state.access_token }),
        headers: { "Content-type": "application/json" },
    });

    const result = await tokenResponse.json();

    console.log(result);

    return tokenResponse.ok || result.active;
}

function refreshToken() {
    throw new Error("Not implemented: refreshToken");
}