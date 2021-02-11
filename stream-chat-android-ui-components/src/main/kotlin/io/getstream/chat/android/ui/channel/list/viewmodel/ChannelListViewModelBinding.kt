@file:JvmName("ChannelListViewModelBinding")

package io.getstream.chat.android.ui.channel.list.viewmodel

import androidx.lifecycle.LifecycleOwner
import io.getstream.chat.android.ui.channel.list.ChannelListView
import io.getstream.chat.android.ui.channel.list.adapter.ChannelListItem

@JvmName("bind")
public fun ChannelListViewModel.bindView(
    view: ChannelListView,
    lifecycle: LifecycleOwner
) {
    state.observe(lifecycle) { channelState ->
        when (channelState) {
            is ChannelListViewModel.State.NoChannelsAvailable -> {
                view.showEmptyStateView()
                view.hideLoadingView()
            }
            is ChannelListViewModel.State.Loading -> {
                view.hideEmptyStateView()
                if (!view.hasChannels()) {
                    view.showLoadingView()
                }
            }
            is ChannelListViewModel.State.Result -> {
                channelState
                    .channels
                    .map(ChannelListItem::ChannelItem)
                    .let(view::setChannels)

                view.hideLoadingView()
                view.hideEmptyStateView()
            }
        }
    }

    paginationState.observe(lifecycle) {
        view.setPaginationEnabled(!it.endOfChannels && !it.loadingMore)

        if (it.loadingMore) {
            view.showLoadingMore()
        } else {
            view.hideLoadingMore()
        }
    }

    view.setOnEndReachedListener {
        onAction(ChannelListViewModel.Action.ReachedEndOfList)
    }
}