package com.me.presentation.di

import androidx.room.Room
import com.me.data.datasource.PostRemoteImpl
import com.me.data.datasource.cache.PostCacheImpl
import com.me.data.db.AppDatabase
import com.me.domain.api.PostsApi
import com.me.domain.datasource.PostCacheDataSource
import com.me.domain.datasource.PostRemoteDataSource
import com.me.domain.repositories.*
import com.me.domain.repository.PostDataSourceFactory
import com.me.domain.usecases.PostUseCase
import com.me.presentation.BuildConfig
import com.me.presentation.postdetails.PostDetailsViewModel
import com.me.presentation.postlist.PostListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        viewModelModule,
        useCaseModule,
        repositoryModule,
        pagingDataSourceFacory,
        dataSourceModule,
        networkModule,
        localModules
    )
}

val viewModelModule: Module = module {
    viewModel { PostListViewModel(postUseCase = get()) }
    viewModel { PostDetailsViewModel(postUseCase = get()) }
}

val useCaseModule: Module = module {
    factory { PostUseCase(postRepository = get()) }

}

val repositoryModule: Module = module {
    single { PostRepositoryImpl(postCacheDataSource = get(), postRemoteDataSource = get(), dataSourceFactory = get() ) as PostRepository }

}

val pagingDataSourceFacory: Module = module {
    single { PostDataSourceFactory(get()) }
}

val dataSourceModule: Module = module {
    single { PostCacheImpl(database = get(DATABASE)) as PostCacheDataSource }
    single { PostRemoteImpl(api = postsApi) as PostRemoteDataSource }
}

val networkModule: Module = module {
    single { postsApi }
}

val localModules = module {
    single(name = DATABASE) {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "fake_posts").build()
    }
}

private const val BASE_URL = "https://my-json-server.typicode.com/MohamedHatemAbdu/fake-posts-jsonholder/"

private val retrofit: Retrofit = createNetworkClient(BASE_URL, BuildConfig.DEBUG)

private val postsApi: PostsApi = retrofit.create(PostsApi::class.java)

private const val DATABASE = "database"