import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dimplebooks.data.AppDatabase
import com.example.dimplebooks.data.entity.bookHistory
import kotlinx.coroutines.launch

class bookHistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val bookHistoryDao = AppDatabase.getDatabase(application).bookHistoryDao()
    private val _bookHistory = MutableLiveData<List<bookHistory>>()
    val bookHistory: LiveData<List<bookHistory>> get() = _bookHistory

    fun insertBook(book: bookHistory) {
        viewModelScope.launch {
            bookHistoryDao.insertBookHistory(book)
        }
    }

    fun getBookHistory() {
        viewModelScope.launch {
            val history = bookHistoryDao.getAllHistory()
            _bookHistory.postValue(history)
        }
    }
    fun updateBookHistory(history: List<bookHistory>) {
        _bookHistory.value = history
    }
}
