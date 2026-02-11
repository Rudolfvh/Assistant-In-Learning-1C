document.addEventListener('DOMContentLoaded', () => {
    const backBtn = document.getElementById('backBtn');
    const form = document.getElementById('createTopicForm');
    const authBtn = document.getElementById('openAuthModal');
    const avatarImg = document.getElementById('profileAvatar');

    // --- Кнопка "Назад" ---
    if (backBtn) {
        backBtn.addEventListener('click', () => {
            window.location.href = '../index.html';
        });
    }

    // --- Проверка авторизации ---
    async function updateAuthButton() {
        if (!authBtn || !avatarImg) return;

        try {
            const response = await fetch('/api/users/me', { credentials: 'include' });
            if (!response.ok) throw new Error('Не авторизован');

            const user = await response.json();

            authBtn.textContent = 'Профиль';
            authBtn.onclick = () => window.location.href = '/profile.html';

            if (user.avatarBase64) {
                avatarImg.src = `data:image/png;base64,${user.avatarBase64}`;
                avatarImg.style.display = 'inline-block';
            } else {
                avatarImg.src = 'images/default-avatar.png';
                avatarImg.style.display = 'inline-block';
            }

        } catch {
            authBtn.textContent = 'Войти';
            authBtn.onclick = () => {
                const modal = document.getElementById('authModal');
                if (modal) modal.style.display = "block";
            };
            avatarImg.style.display = 'none';
        }
    }

    // --- Отправка формы создания темы ---
    if (form) {
        form.addEventListener('submit', async (e) => {
            e.preventDefault();

            const title = document.getElementById('topicTitle').value.trim();
            const description = document.getElementById('topicDescription').value.trim();
            const theory = document.getElementById('lessonTheory').value.trim();
            const codeExample = document.getElementById('lessonCode').value.trim();

            if (!title || !description || !theory || !codeExample) {
                alert("Все поля обязательны для заполнения!");
                return;
            }

            const dto = { title, description, theory, codeExample };

            try {
                const response = await fetch('/api/topics', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    credentials: 'include',
                    body: JSON.stringify(dto)
                });

                if (!response.ok) {
                    const errText = await response.text();
                    throw new Error(errText || 'Ошибка при создании темы');
                }

                const createdTopic = await response.json();
                alert(`Тема "${createdTopic.title}" создана!`);
                window.location.href = '../index.html';

            } catch (err) {
                console.error(err);
                alert("Не удалось создать тему: " + err.message);
            }
        });
    }

    // --- Инициализация ---
    updateAuthButton();
});
