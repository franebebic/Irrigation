<#import "template.ftl" as layout>

<@layout.registrationLayout section>

    <#if section == "form">

        <div class="si-wrap">

            <!-- LEFT COLUMN -->
            <aside class="si-left">
                <img
                        src="${url.resourcesPath}/img/login-illustration.png"
                        alt="SmartIrrigation"
                        class="si-hero"
                />
            </aside>

            <!-- RIGHT COLUMN -->
            <main class="si-right">
                <h1 class="si-title">Sign in</h1>

                <#if realm.password>
                    <form id="kc-form-login"
                          action="${url.loginAction}"
                          method="post">

                        <div class="si-field">
                            <label for="username" class="si-label">${msg("usernameOrEmail")}</label>
                            <input id="username" name="username" type="text"
                                   value="${(login.username!'')}"
                                   class="si-input" autofocus />
                        </div>

                        <div class="si-field">
                            <label for="password" class="si-label">${msg("password")}</label>
                            <input id="password" name="password" type="password"
                                   class="si-input" />
                        </div>

                        <button id="kc-login" name="login" type="submit" class="si-button">
                            ${msg("doLogIn")}
                        </button>

                    </form>
                </#if>

            </main>

        </div>

    </#if>

</@layout.registrationLayout>
