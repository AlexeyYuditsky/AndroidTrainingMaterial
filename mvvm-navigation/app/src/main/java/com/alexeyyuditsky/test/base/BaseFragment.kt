package com.alexeyyuditsky.test.base

import androidx.fragment.app.Fragment

// класс от которого наследуются все фрагменты, класс определяет свойство viewModel, которое необходимо
// переопределить в каждом фрагменте
abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel

}