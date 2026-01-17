<#import "template.ftl" as layout>

<@layout.registrationLayout; section>

    <#if section = "form">

        <div class="si-wrap">

            <aside class="si-left">


                <div class="si-left-text">
                    <img class="si-hero"
                         src="${url.resourcesPath}/img/login-illustration.png"
                         alt="SmartIrrigation" />
                    <p class="si-left-subtitle">
                        Automate watering.<br/>
                        Track soil moisture.<br/>
                        Save water.
                    </p>
                </div>
            </aside>


            <main class="si-right">
                <h1 class="si-title">Sign in</h1>
                <p class="si-subtitle">Continue to your garden dashboard</p>

                <#if realm.password>
                    <form id="kc-form-login"
                          action="${url.loginAction}"
                          method="post">

                        <div class="si-field">
                            <label for="username" class="si-label">${msg("usernameOrEmail")}</label>
                            <input id="username" name="username" type="text"
                                   class="si-input"
                                   value="${(login.username!'')}"
                                   autofocus autocomplete="off" />
                        </div>

                        <div class="si-field">
                            <label for="password" class="si-label">${msg("password")}</label>
                            <input id="password" name="password" type="password"
                                   class="si-input"
                                   autocomplete="off" />
                        </div>

                        <#if (realm.rememberMe || realm.resetPasswordAllowed) && !(usernameHidden?? && usernameHidden)>
                            <div class="si-row">
                                <#if realm.rememberMe>
                                    <label class="si-checkbox">
                                        <input id="rememberMe" name="rememberMe" type="checkbox"
                                               <#if login.rememberMe??>checked</#if> />
                                        <span>${msg("rememberMe")}</span>
                                    </label>
                                <#else>
                                    <span></span>
                                </#if>

                                <#if realm.resetPasswordAllowed>
                                    <a class="si-link" href="${url.loginResetCredentialsUrl}">
                                        ${msg("doForgotPassword")}
                                    </a>
                                </#if>
                            </div>
                        </#if>


                        <input type="hidden" id="id-hidden-input" name="credentialId" />

                        <button id="kc-login" name="login" type="submit" class="si-button">
                            ${msg("doLogIn")}
                        </button>
                    </form>
                </#if>

                <footer class="si-footer">
                    <span class="si-powered">Powered by Keycloak</span>
                    <small class="si-copy">© 2026 Frane Bebić · v1.0.0</small>
                </footer>
            </main>
        </div>

    </#if>

</@layout.registrationLayout>
