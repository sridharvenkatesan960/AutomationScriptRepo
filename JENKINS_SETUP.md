# Jenkins CI/CD Pipeline Setup Guide

This guide will help you set up Jenkins CI/CD pipeline for the Automation Test Framework.

## Prerequisites

### 1. Jenkins Installation
- Jenkins 2.x or higher
- Minimum 2GB RAM, 4GB recommended
- Java JDK 17 installed

### 2. Required Jenkins Plugins
Install these plugins from **Manage Jenkins → Manage Plugins**:

- **Pipeline Plugin** (for Jenkinsfile support)
- **Git Plugin** (for GitHub integration)
- **Maven Integration Plugin**
- **Cucumber Reports Plugin**
- **HTML Publisher Plugin**
- **Email Extension Plugin** (optional, for notifications)
- **Build Timeout Plugin** (recommended)

## Jenkins Configuration Steps

### Step 1: Configure Global Tools

Go to **Manage Jenkins → Global Tool Configuration**

#### Configure Maven:
1. Click **Add Maven**
2. Name: `Maven-3.9.0` (must match Jenkinsfile)
3. Check "Install automatically"
4. Select Maven version: `3.9.0` or latest

#### Configure JDK:
1. Click **Add JDK**
2. Name: `JDK-17` (must match Jenkinsfile)
3. Check "Install automatically"
4. Select JDK version: `17` or latest LTS

### Step 2: Create Jenkins Pipeline Job

1. **New Item → Pipeline**
   - Name: `Naukri-Automation-Tests`
   - Type: Pipeline
   - Click OK

2. **Configure Pipeline:**

   **General:**
   - ✅ GitHub project
   - Project URL: `https://github.com/sridharvenkatesan960/AutomationScriptRepo`

   **Build Triggers:**
   - ✅ GitHub hook trigger for GITScm polling (for automatic builds on push)
   - ✅ Poll SCM: `H/15 * * * *` (check every 15 minutes)

   **Pipeline:**
   - Definition: `Pipeline script from SCM`
   - SCM: `Git`
   - Repository URL: `git@github.com:sridharvenkatesan960/AutomationScriptRepo.git`
   - Credentials: Add your SSH key or GitHub token
   - Branch: `*/main`
   - Script Path: `Jenkinsfile`

3. **Click Save**

### Step 3: Configure GitHub Webhook (Optional - for auto-trigger)

1. Go to GitHub repository settings
2. Navigate to **Settings → Webhooks → Add webhook**
3. Payload URL: `http://your-jenkins-url/github-webhook/`
4. Content type: `application/json`
5. Select: `Just the push event`
6. Click **Add webhook**

### Step 4: Add Credentials to Jenkins

Go to **Manage Jenkins → Manage Credentials → Global → Add Credentials**

#### Option A: SSH Key (Recommended)
- Kind: `SSH Username with private key`
- ID: `github-ssh-key`
- Username: `git`
- Private Key: Paste your SSH private key content from `~/.ssh/id_ed25519`

#### Option B: GitHub Personal Access Token
- Kind: `Username with password`
- Username: Your GitHub username
- Password: Your GitHub Personal Access Token
- ID: `github-token`

## Running the Pipeline

### Manual Execution:
1. Go to your pipeline job
2. Click **Build with Parameters**
3. Select parameters:
   - **BROWSER:** chrome/firefox/edge
   - **ENVIRONMENT:** dev/qa/staging/prod
   - **TEST_TAGS:** @login, @smoke, @regression
4. Click **Build**

### Automatic Execution:
- Push code to GitHub → Webhook triggers Jenkins → Pipeline runs automatically

## Pipeline Stages Overview

1. **Checkout** - Pulls latest code from GitHub
2. **Build** - Compiles the project using Maven
3. **Unit Tests** - Runs TestNG unit tests
4. **Integration Tests** - Executes Cucumber BDD tests
5. **Generate Reports** - Creates Cucumber test reports
6. **Archive Artifacts** - Saves test reports and logs
7. **Code Quality Analysis** - Optional SonarQube integration

## Viewing Test Reports

After pipeline execution:
1. Click on the build number (e.g., #1, #2)
2. View reports:
   - **Console Output** - Full execution logs
   - **Cucumber Reports** - Interactive test results
   - **Artifacts** - Downloaded HTML reports

## Troubleshooting

### Issue: Maven/JDK not found
**Solution:** Verify tool names in Jenkinsfile match Global Tool Configuration

### Issue: Permission denied (GitHub)
**Solution:** Add correct SSH key or GitHub token to Jenkins credentials

### Issue: Tests failing
**Solution:** Check Console Output for error details

### Issue: Browser not launching
**Solution:** Ensure Chrome/Firefox is installed on Jenkins agent

## Advanced Configuration

### Email Notifications
Uncomment email sections in Jenkinsfile and configure:
- **Manage Jenkins → Configure System → Extended E-mail Notification**
- SMTP server, port, credentials

### SonarQube Integration
1. Install SonarQube Scanner plugin
2. Configure SonarQube server in Jenkins
3. Uncomment SonarQube step in Jenkinsfile

### Parallel Execution
Modify Jenkinsfile to run tests in parallel for faster execution

## Best Practices

- ✅ Use parameterized builds for flexibility
- ✅ Archive test reports for historical analysis
- ✅ Set up email notifications for failures
- ✅ Use GitHub webhooks for automatic builds
- ✅ Keep Jenkins and plugins updated
- ✅ Use Jenkins agents for distributed execution

## Support

For issues or questions:
- Check Jenkins logs: `/var/log/jenkins/jenkins.log`
- Review build Console Output
- GitHub Issues: Create issue in repository
