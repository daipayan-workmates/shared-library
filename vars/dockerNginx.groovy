// vars/dockerNginx.groovy
def buildAndRunNginx(String dockerTag = 'nginx-custom:latest', String customText = 'Hello, Jenkins and NGINX!') {
    // Write the custom index.html content to a temporary file
    def indexFile = "index.html"
    writeFile(file: indexFile, text: """
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Custom NGINX Page</title>
    </head>
    <body>
        <h1>${customText}</h1>
    </body>
    </html>
    """)

    // Dockerfile content
    def dockerFileContent = """
    FROM nginx:latest
    COPY index.html /usr/share/nginx/html/index.html
    """
    writeFile(file: "Dockerfile", text: dockerFileContent)

    // Build Docker image
    sh "docker build -t ${dockerTag} ."

    // Run Docker container
    sh "docker run -d -p 8080:80 ${dockerTag}"

    // Cleanup files after the build
    sh "rm Dockerfile ${indexFile}"
}

