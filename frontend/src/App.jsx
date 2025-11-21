import { useState, useEffect } from 'react';

const API = 'http://localhost:8081/api/menu';

function App() {
    const [lang, setLang] = useState('ru');
    const [languages, setLanguages] = useState([]);
    const [categories, setCategories] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState(null);
    const [items, setItems] = useState([]);

    useEffect(() => {
        fetch(`${API}/languages`)
            .then(r => r.json())
            .then(setLanguages);
    }, []);

    useEffect(() => {
        if (lang) {
            fetch(`${API}/${lang}/categories`)
                .then(r => r.json())
                .then(setCategories);
            setSelectedCategory(null);
        }
    }, [lang]);

    const loadItems = (catId) => {
        fetch(`${API}/${lang}/category/${catId}`)
            .then(r => r.json())
            .then(setItems);
        setSelectedCategory(catId);
    };

    const back = () => {
        setSelectedCategory(null);
        setItems([]);
    };

    return (
        <div className="min-h-screen bg-gray-50">
            <div className="bg-amber-700 text-white p-4 shadow-lg">
                <div className="max-w-4xl mx-auto flex justify-between items-center">
                    <h1 className="text-2xl font-bold">Кофейня ☕️</h1>
                    <select
                        value={lang}
                        onChange={e => setLang(e.target.value)}
                        className="bg-amber-800 px-4 py-2 rounded-lg"
                    >
                        {languages.map(l => (
                            <option key={l.id} value={l.code}>{l.name}</option>
                        ))}
                    </select>
                </div>
            </div>

            {!selectedCategory ? (
                <div className="max-w-6xl mx-auto p-6 grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
                    {categories.map(cat => (
                        <div
                            key={cat.id}
                            onClick={() => loadItems(cat.id)}
                            className="bg-white rounded-2xl shadow-xl overflow-hidden cursor-pointer hover:scale-105 transition"
                        >
                            {cat.imageUrl ? (
                                <img src={cat.imageUrl} alt={cat.name} className="w-full h-48 object-cover" />
                            ) : (
                                <div className="bg-gray-200 border-2 border-dashed w-full h-48" />
                            )}
                            <div className="p-4 text-center">
                                <h3 className="text-xl font-semibold">{cat.name}</h3>
                            </div>
                        </div>
                    ))}
                </div>
            ) : (
                <div className="max-w-4xl mx-auto p-6">
                    <button onClick={back} className="mb-6 text-amber-700 font-bold text-lg">
                        ← Назад
                    </button>
                    <div className="grid gap-6">
                        {items.map(item => (
                            <div key={item.id} className="bg-white rounded-xl shadow-lg overflow-hidden flex">
                                {item.imageUrl ? (
                                    <img src={item.imageUrl} alt={item.name} className="w-32 h-32 object-cover" />
                                ) : (
                                    <div className="bg-gray-200 w-32 h-32" />
                                )}
                                <div className="p-4 flex-1">
                                    <h3 className="text-xl font-bold">{item.name}</h3>
                                    {item.description && <p className="text-gray-600 mt-1">{item.description}</p>}
                                    <p className="text-2xl font-bold text-amber-700 mt-4">{item.price} ₽</p>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            )}
        </div>
    );
}

export default App;
