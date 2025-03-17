async function registerUser() {
    let username = document.getElementById("reg-username").value;
    let password = document.getElementById("reg-password").value;

    let response = await fetch("/user", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `username=${username}&password=${password}`
    });

    alert(response.status === 200 ? "Registration successful!" : "User already exists!");
}

async function sendMessage() {
    let username = document.getElementById("msg-username").value;
    let message = document.getElementById("msg-content").value;

    let response = await fetch("/message", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `username=${username}&message=${message}`
    });

    alert(response.status === 200 ? "Message sent!" : "Failed to send message!");
}

async function getMessages() {
    let username = document.getElementById("inbox-username").value;
    let password = document.getElementById("inbox-password").value;

    let response = await fetch(`/message?username=${username}&password=${password}`);

    if (response.status === 200) {
        let messages = await response.json();
        document.getElementById("messages-list").innerHTML = messages.map(msg => `<li>${msg}</li>`).join("");
    } else {
        alert("Failed to retrieve messages!");
    }
}
