const topicsList = document.getElementById('topicsList');
const searchInput = document.getElementById('searchInput');

function loadTopics(search = '') {
    let url = '/api/topics';
    if (search) {
        url += '?search=' + encodeURIComponent(search);
    }

    fetch(url)
        .then(response => response.json())
        .then(data => renderTopics(data));
}

function renderTopics(topics) {
    topicsList.innerHTML = '';

    if (topics.length === 0) {
        topicsList.innerHTML = '<div>Темы не найдены</div>';
        return;
    }

    topics.forEach(topic => {
        const div = document.createElement('div');
        div.className = 'topic-item';
        div.textContent = topic.title;

        div.onclick = () => {
            window.location.href = `/topic.html?id=${topic.id}`;
        };

        topicsList.appendChild(div);
    });
}

// поиск в реальном времени
searchInput.addEventListener('input', () => {
    loadTopics(searchInput.value);
});

// первая загрузка
loadTopics();
