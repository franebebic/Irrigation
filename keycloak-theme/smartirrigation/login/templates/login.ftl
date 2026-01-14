<#import "template.ftl" as layout>

<@layout.registrationLayout displayMessage=!messagesPerField.existsError('username','password') displayInfo=true; section>
    <#if section = "header">
        <!-- header se namjerno prazni, ne koristimo Keycloak default header -->
    <#elseif section = "form">

        <body>
        <div class="login-pf-page">

            <!-- CARD -->
            <div class="card-pf si-card">

                <!-- LEFT PANEL -->
                <div class="si-left">
                    <div class="si-brand">
                        <div class="si-brand-mark">
                            <!-- Ovdje može i SVG -->
                            🌱
                        </div>
                        <div class="si-brand-title">SmartIrrigation</div>
                    </div>

                    <img
                            class="si-illustration"
                            src="${url.resourcesPath}/img/login-illustration.png"
                            alt="Smart irrigation illustration"
                    />

                    <div class="si-tagline">
                        <span>Water smarter.</span>
                        <span>Grow healthier.</span>
                        <span class="muted">Control your garden with confidence.</span>
                    </div>
                </div>

                <!-- RIGHT PANEL -->
                <div class="si-right">

                    <!-- TITLE -->
                    <header class="login-pf-header">
                        <h1 id="kc-page-title">
                            ${msg("loginTitle")}
                        </h1>
                        <p class="si-subtitle">
                            Continue to your garden dashboard
                        </p>
                    </header>

                    <!-- FORM -->
                    <div id="kc-content">
                        <div id="kc-content-wrapper">

                            <#if realm.password>
                                <form
                                        id="kc-form-login"
                                        onsubmit="login.disabled = true; return true;"
                                        action="${url.loginAction}"
                                        method="post"
                                >

                                    <!-- USERNAME -->
                                    <div class="form-group">
                                        <label for="username" class="pf-c-form__label pf-c-form__label-text">
                                            ${msg("usernameOrEmail")}
                                        </label>

                                        <input
                                                tabindex="1"
                                                id="username"
                                                class="pf-c-form-control"
                                                name="username"
                                                value="${(login.username!'')}"
                                                type="text"
                                                autofocus
                                                autocomplete="off"
                                        />
                                    </div>

                                    <!-- PASSWORD -->
                                    <div class="form-group">
                                        <label for="password" class="pf-c-form__label pf-c-form__label-text">
                                            ${msg("password")}
                                        </label>

                                        <div class="pf-c-input-group">
                                            <input
                                                    tabindex="2"
                                                    id="password"
                                                    class="pf-c-form-control"
                                                    name="password"
                                                    type="password"
                                                    autocomplete="off"
                                            />

                                            <button
                                                    class="pf-c-button pf-m-control"
                                                    type="button"
                                                    aria-label="${msg("showPassword")}"
                                                    data-password-toggle
                                                    data-label-show="${msg("showPassword")}"
                                                    data-label-hide="${msg("hidePassword")}"
                                            >
                                                <i class="fa fa-eye" aria-hidden="true"></i>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- OPTIONS (remember me, etc.) -->
                                    <#if realm.rememberMe && !usernameHidden??>
                                        <div class="form-group login-pf-settings">
                                            <div id="kc-form-options">
                                                <div class="checkbox">
                                                    <label>
                                                        <input
                                                                tabindex="3"
                                                                id="rememberMe"
                                                                name="rememberMe"
                                                                type="checkbox"
                                                                <#if login.rememberMe??>checked</#if>
                                                        />
                                                        ${msg("rememberMe")}
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </#if>

                                    <!-- BUTTON -->
                                    <div id="kc-form-buttons" class="form-group">
                                        <input
                                                type="hidden"
                                                id="id-hidden-input"
                                                name="credentialId"
                                        />
                                        <input
                                                tabindex="4"
                                                class="pf-c-button pf-m-primary pf-m-block btn-lg"
                                                name="login"
                                                id="kc-login"
                                                type="submit"
                                                value="${msg("doLogIn")}"
                                        />
                                    </div>

                                </form>
                            </#if>

                        </div>
                    </div>

                    <!-- FOOTER -->
                    <div class="si-footer">
                        © 2026 SmartIrrigation · Secure access powered by Keycloak
                    </div>

                </div>
                <!-- /RIGHT PANEL -->

            </div>
            <!-- /CARD -->

        </div>

        <script type="module" src="${url.resourcesPath}/js/passwordVisibility.js"></script>
        </body>

    </#if>
</@layout.registrationLayout>
