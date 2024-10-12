// Handle form submission via AJAX for post creation
document.getElementById("postForm").addEventListener("submit", function(event) {
    event.preventDefault();
    const formData = new FormData(this);

    fetch('/posts/create', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            title: formData.get('title'),
            content: formData.get('content')
        })
    })
    .then(response => response.json())
    .then(post => {
        const postContainer = document.createElement("div");
        postContainer.innerHTML = `
            <h2>${post.title}</h2>
            <p>${post.content}</p>
            <h3>Replies:</h3>
            <ul id="replies-${post.id}"></ul>
            <form onsubmit="submitReply(event, ${post.id})">
                <label>Your Reply:</label>
                <textarea name="content"></textarea>
                <button type="submit">Submit</button>
            </form>
        `;
        document.getElementById("posts").appendChild(postContainer);
    })
    .catch(error => console.error('Error:', error));
});

// Handle reply form submission via AJAX
function submitReply(event, postId) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);

    fetch(`/posts/${postId}/reply`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ content: formData.get('content') })
    })
    .then(response => response.json())
    .then(reply => {
        const replyList = document.getElementById(`replies-${postId}`);
        const newReply = document.createElement("li");
        newReply.textContent = reply.content;
        replyList.appendChild(newReply);
        form.reset();
    })
    .catch(error => console.error('Error:', error));
}
