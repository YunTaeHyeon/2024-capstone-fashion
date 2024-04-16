function editComment(button) {
    const comment_id = button.getAttribute('data-id');
    const commentElement = document.getElementById('comment-' + comment_id);
    const commentContent = commentElement.textContent;

    const newContent = prompt('Edit your comment:', commentContent);
    if (newContent) {
        $.post(`/post/${id}/comment/${comment_id}/update`, {content: newContent}, function(data) {
            window.location.href = data;
        });
    }
}