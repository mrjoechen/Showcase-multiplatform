import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alpha.networkfile.storage.external.GitHubSource
import com.alpha.networkfile.storage.external.TMDBSource
import com.alpha.showcase.common.repo.github.GithubFileRepo
import com.alpha.showcase.common.repo.sources.SourceListRepo
import com.alpha.showcase.common.repo.tmdb.NOW_PLAYING_MOVIES
import com.alpha.showcase.common.repo.tmdb.TmdbSourceRepo
import com.alpha.showcase.common.repo.tmdb.data.ImageType
import com.alpha.showcase.common.repo.tmdb.data.Language
import com.alpha.showcase.common.repo.tmdb.data.Region
import com.alpha.showcase.common.ui.MainNavHost
import com.alpha.showcase.common.utils.get
import io.ktor.client.HttpClient
import io.ktor.http.URLProtocol

@Composable
fun App() {
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            MainNavHost()
        }
    }

    LaunchedEffect(Unit) {
        val sourceListRepo = SourceListRepo()
        sourceListRepo.addSource(GitHubSource("aaaa", "bbbb", "cccc"))

    }

}

