#!/usr/bin/env groovy

import hudson.security.*
import jenkins.model.*
import hudson.security.csrf.DefaultCrumbIssuer
import jenkins.security.s2m.AdminWhitelistRule

def instance = Jenkins.getInstance()
def hudsonRealm = new HudsonPrivateSecurityRealm(false)

// Creates 'admin' user
println "--> creating local admin user"
hudsonRealm.createAccount('admin', 'r4nd0mP4$$W0rd')
instance.setSecurityRealm(hudsonRealm)

// Disables remote CLI
jenkins.CLI.get().setEnabled(false)

// Sets root URL
jlc = JenkinsLocationConfiguration.get()
jlc.setUrl("http://localhost:9089/") 
jlc.save() 

// Configures CSRF protection in global security settings.
if(instance.getCrumbIssuer() == null)
  instance.setCrumbIssuer(new DefaultCrumbIssuer(true))

// Sets authentication straetgy
def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)

// Enable Jenkins agent to master access control
instance.injector.getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false);

instance.save()
