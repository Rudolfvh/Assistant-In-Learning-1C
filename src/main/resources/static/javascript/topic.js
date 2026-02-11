document.addEventListener('DOMContentLoaded', () => {
    const backBtn = document.getElementById('backBtn');
    const authBtn = document.getElementById('openAuthModal'); // кнопка Войти/Профиль
    const avatarImg = document.getElementById('profileAvatar');

    // --- Кнопка "Назад" ---
    if (backBtn) {
        backBtn.addEventListener('click', () => {
            window.location.href = '../index.html';
        });
    }

    // --- Функция проверки авторизации ---
    async function updateAuthButton() {
        if (!authBtn || !avatarImg) return;

        try {
            const response = await fetch('/api/users/me', { credentials: 'include' });
            if (!response.ok) throw new Error('Не авторизован');

            const user = await response.json();

            // Пользователь авторизован
            authBtn.textContent = 'Профиль';
            authBtn.onclick = () => window.location.href = '/profile.html';

            // Показываем аватар
            if (user.avatarBase64) {
                avatarImg.src = `data:image/png;base64,${user.avatarBase64}`;
                avatarImg.style.display = 'inline-block';
            } else {
                avatarImg.src = 'images/default-avatar.png';
                avatarImg.style.display = 'inline-block';
            }

        } catch {
            // Пользователь не авторизован
            authBtn.textContent = 'Войти';
            authBtn.onclick = () => {
                const modal = document.getElementById('authModal');
                if (modal) modal.style.display = "block";
            };
            avatarImg.style.display = 'none';
        }
    }

    // --- Подгрузка темы ---
    const params = new URLSearchParams(window.location.search);
    const topicId = params.get('id');

    if (!topicId) {
        alert("Не указан ID темы!");
        return;
    }

    fetch(`/api/topics/${topicId}`)
        .then(res => {
            if (!res.ok) throw new Error("Ошибка загрузки темы");
            return res.json();
        })
        .then(data => {
            document.getElementById('topic-title').textContent = data.title;
            document.getElementById('topic-description').textContent = data.description;
            document.getElementById('lesson-theory').textContent = data.theory;
            document.getElementById('lesson-code').textContent = data.codeExample;
        })
        .catch(err => {
            console.error(err);
            document.getElementById('topic-title').textContent = "Ошибка загрузки темы";
        });

    // --- Инициализация ---
    updateAuthButton();
});
