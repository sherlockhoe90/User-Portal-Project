// Add an event listener to the form submission
document.getElementById("passwordForm").addEventListener("submit", function(event) {
    // Get the values of both password fields
    var newPassword = document.getElementById("pwd").value;
    var confirmPassword = document.getElementById("cpwd").value;

    // Check if passwords match
    if (newPassword !== confirmPassword) {
        // Display error message
        document.getElementById("confirm_password_error").innerText = "Passwords do not match!";
        // Prevent form submission
        event.preventDefault();
    }
    // If passwords match, no need to do anything, the form will be submitted normally
});