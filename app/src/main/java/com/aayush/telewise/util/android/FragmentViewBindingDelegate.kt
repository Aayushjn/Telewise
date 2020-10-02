package com.aayush.telewise.util.android

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A delegate for setting up [ViewBinding] in [Fragment]s
 *
 * The code used here is taken from Gabor Varadi's Medium article
 * (https://medium.com/@Zhuinden/simple-one-liner-viewbinding-in-fragments-and-activities-with-kotlin-961430c6c07c)
 */
class FragmentViewBindingDelegate<VB: ViewBinding>(
    val fragment: Fragment,
    val viewBindingFactory: (View) -> VB
) : ReadOnlyProperty<Fragment, VB> {
    private var binding: VB? = null

    init {
        fragment.lifecycle.addObserver(
            object : DefaultLifecycleObserver {
                override fun onCreate(owner: LifecycleOwner) {
                    fragment.viewLifecycleOwnerLiveData.observe(fragment) { lifecycleOwner ->
                        lifecycleOwner.lifecycle.addObserver(
                            object : DefaultLifecycleObserver {
                                override fun onDestroy(owner: LifecycleOwner) {
                                    binding = null
                                }
                            }
                        )
                    }
                }
            }
        )
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        val binding = this.binding
        if (binding != null) return binding

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Attempting to get bindings when fragment views are destroyed!")
        }
        return viewBindingFactory(thisRef.requireView()).also { this.binding = it }
    }
}

fun <VB: ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> VB) =
    FragmentViewBindingDelegate(this, viewBindingFactory)

inline fun <VB : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> VB
) = lazy(LazyThreadSafetyMode.NONE) { bindingInflater(layoutInflater) }
