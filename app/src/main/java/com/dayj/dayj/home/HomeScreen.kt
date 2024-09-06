package com.dayj.dayj.home

import com.dayj.dayj.home.component.calendar.HorizontalCalendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dayj.dayj.home.component.PlanTagSelector
import com.dayj.dayj.home.component.TodoList
import com.dayj.dayj.network.api.response.PlanResponse
import com.dayj.dayj.ui.theme.DividerColor


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navToAddToDo: () -> Unit,
    navToUpdateTodo : (PlanResponse) -> Unit
) {

    val state = viewModel.homeViewState.collectAsState().value

    LaunchedEffect(key1 = Unit) {
        viewModel.getPlans(date = state.selectedData)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalCalendar(
            selectedDate = state.selectedData,
            updateSelectedDate = viewModel::updateDate,
            onRouteTodo = navToAddToDo,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            thickness = 1.dp,
            color = DividerColor
        )

        PlanTagSelector(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp),
            selectTag = state.selectTag,
            onChangedPlanTag = viewModel::changeSelectPlanTag
        )

        TodoList(
            list = state.getFilterPlans(),
            onUpdatePlanCheck = viewModel::updatePlanCheck,
            onItemClick = {},
            onDelete = viewModel::deletePlan,
            onRouteUpdate = navToUpdateTodo
        )
    }
}