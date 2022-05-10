package ru.yundon.shoplist.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.yundon.shoplist.presentation.activity.MainActivity
import ru.yundon.shoplist.presentation.fragment.ShopItemFragment

@Component(modules = [DataModule::class, DomainModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: ShopItemFragment)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}